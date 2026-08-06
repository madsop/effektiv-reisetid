import React, {useEffect} from 'react';
import './App.css';
import axios from "axios";

interface Reiserute {
    medTog: Alternativ;
    medFly: Alternativ;
}

interface Alternativ {
    tekst: string;
    strekninger: Delstrekning[];
}

interface Delstrekning {
    fra: string;
    til: string;
    type: string;
    varighet: number;
}

const totalVarigheit = (alternativ: Alternativ) => {
    const minutt = alternativ.strekninger.map(delstrekning => delstrekning.varighet).reduce((a, b) => a + b, 0);
    return formaterVarigheit(minutt);
}

function formaterVarigheit(minutt: number) {
    const timar = Math.floor(minutt / 60);
    if (timar > 0) {
        const timetekst = timar + " timar";
        if ((minutt % 60) > 0) {
            return timetekst + " og " + (minutt % 60) + " minutt";
        }
        return timetekst;
    } else  {
        return minutt + " minutt";
    }
}

function App() {
    const [startby, setStartby] = React.useState<string>("Oslo");
    const [sluttby, setSluttby] = React.useState<string>("Trondheim");
    const [reiserute, setReiserute] = React.useState<Reiserute | undefined>(undefined);

    useEffect(() => {
        axios
            .get("http://localhost:8080/reise?startby=" + startby + "&sluttby=" + sluttby)
            .then((data) => {
                setReiserute(data.data);
            });
    }, [startby, sluttby]);

    function lagReiserute(alternativ: Alternativ) {
        return <>
            <header className="App-header">
                <h1>{alternativ.tekst}</h1>
            </header>
            <table className={"reiserute"}>
                <thead>
                <tr>
                    <th>Fra</th>
                    <th>Til</th>
                    <th>Varigheit</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody>
                {alternativ.strekninger.map((delstrekning, index) => (
                    <tr key={delstrekning.fra + delstrekning.til + index}>
                        <td>{delstrekning.fra}</td>
                        <td>{delstrekning.til}</td>
                        <td>{formaterVarigheit(delstrekning.varighet)}</td>
                        <td>{formaterType(delstrekning.type)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div className={"varigheit"}>Total varigheit: {totalVarigheit(alternativ)}</div>
        </>;
    }

    return (
        <div className="App">
            Velg startby
            <select
                    value={startby}
                    onChange={(e) => setStartby(e.target.value)}
            >
                {byar.map((by => (<option key={by} value={by}>{by}</option>)))}
            </select>
            |
            Velg sluttby
            <select value={sluttby}
                    onChange={(e) => setSluttby(e.target.value)}
            >
                {byar.map((by => (<option key={by} value={by}>{by}</option>)))}
            </select>
            {reiserute ?
                <>
                    {lagReiserute(reiserute.medTog)}
                    {lagReiserute(reiserute.medFly)}
                </> : <></>
            }
        </div>
    );
}

export default App;

const byar = ["Oslo", "Trondheim", "Bergen", "Stavanger", "Kristiansand"]

const formaterType = (type: string) => {
    switch (type) {
        case "TOG": return "Tog";
        case "FLYTOG": return "Flytog";
        case "SIKKERHEITSKONTROLL": return "Sikkerheitskontroll";
        case "BUFFER": return "Buffer";
        case "VENTE_PAA_BOARDING": return "Vente på boarding";
        case "VENTE_OMBORD": return "Vente ombord";
        case "FLY": return "Fly";
        case "GAA": return "Gå";
        case "VENTE_PAA_TOG": return "Vente på tog";
        default: return "uforventa type " + type;
    }
}