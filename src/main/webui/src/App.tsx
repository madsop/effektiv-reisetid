import React, {useEffect, useState} from 'react';
import './App.css';
import axios from "axios";

function App() {
    const [startby, setStartby] = React.useState<string>("Oslo");
    const [sluttby, setSluttby] = React.useState<string>("Trondheim");
    const [loading, setLoading] = useState(true);
    const backend = "";

    const sort = (a: string, b: string): number => {
        if (!a) return 1;
        if (!b) return 1;
        return a.toLowerCase().localeCompare(b.toLowerCase());
    };

    useEffect(() => {
        axios
            .get(backend + "/reise?startby=" + startby + "&sluttby=" + sluttby)
            // .then((response) => response.data.sort(sort))
            .then((data) => {
                setLoading(false);
            });
    }, [startby, sluttby]);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Reisemåte</h1>
        <p>
          tabell her
        </p>
      </header>
    </div>
  );
}

export default App;
