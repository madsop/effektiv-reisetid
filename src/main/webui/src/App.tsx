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
    const timar = Math.floor(minutt / 60);
    return timar + " timar og " + (minutt % 60) + " minutt";
}

function App() {
    const [startby, setStartby] = React.useState<string>("Oslo");
    const [sluttby, setSluttby] = React.useState<string>("Trondheim");
    const [reiserute, setReiserute] = React.useState<Reiserute | undefined>(undefined);

    useEffect(() => {
        axios
            .get("/reise?startby=" + startby + "&sluttby=" + sluttby)
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
                    <th>Varigheit (minutt)</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody>
                {alternativ.strekninger.map((delstrekning) => (
                    <tr>
                        <td>{delstrekning.fra}</td>
                        <td>{delstrekning.til}</td>
                        <td>{delstrekning.varighet}</td>
                        <td>{delstrekning.type}</td>
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
            <select defaultValue={"Oslo"}
                    value={startby}
                    onChange={(e) => setStartby(e.target.value)}
            >
                {byar.map((by => (<option key={by} value={by}>{by}</option>)))}
            </select>
            |
            Velg sluttby
            <select defaultValue={"Trondheim"}
                    value={sluttby}
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

const byar = ["Oslo", "Trondheim", "Bergen", "Stavanger", "Kristiansand", "Tromsø"]