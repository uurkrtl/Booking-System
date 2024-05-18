import './PageNotFound404.css'

function PageNotFound404() {
    return (
        <div className="body">
            <div className="container">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32" height="72" width="72">
                    <path fill="none" stroke="#4285f4" strokeLinecap="round" strokeLinejoin="round"
                          d="M23.5,27.5H6.5l-1-15.19a.76.76,0,0,1,.77-.81H10a1.11,1.11,0,0,1,.89.44l1.22,1.56H23.5v2"
                          className="colorStroke29abe2 svgStroke"></path>
                    <path fill="none" stroke="#4285f4" strokeLinecap="round" strokeLinejoin="round"
                          d="M26.3,20.7l.84-3.2H9.25L6.5,27.5H23.41a1.42,1.42,0,0,0,1.37-1.06l.76-2.88"
                          className="colorStroke29abe2 svgStroke"></path>
                    <path fill="none" stroke="#4285f4" strokeLinecap="round" strokeLinejoin="round"
                          d="M16.5,24.5h0a1.42,1.42,0,0,1,2,0h0" className="colorStroke29abe2 svgStroke"></path>
                    <line x1="13.5" x2="14.5" y1="21.5" y2="21.5" fill="none" stroke="#4285f4" strokeLinecap="round"
                          strokeLinejoin="round" className="colorStroke29abe2 svgStroke"></line>
                    <line x1="20.5" x2="21.5" y1="21.5" y2="21.5" fill="none" stroke="#4285f4" strokeLinecap="round"
                          strokeLinejoin="round" className="colorStroke29abe2 svgStroke"></line>
                    <path fill="none" stroke="#4285f4" strokeLinecap="round" strokeLinejoin="round"
                          d="M20.62,3.61C18.25,4,16.5,5.37,16.5,7a2.57,2.57,0,0,0,.7,1.7l-.7,2.8,2.86-1.43A8.12,8.12,0,0,0,22,10.5c3,0,5.5-1.57,5.5-3.5,0-1.6-1.69-2.95-4-3.37"
                          className="colorStroke29abe2 svgStroke"></path>
                    <line x1="21.25" x2="22.75" y1="6.25" y2="7.75" fill="none" stroke="#4285f4" strokeLinecap="round"
                          strokeLinejoin="round" className="colorStroke29abe2 svgStroke"></line>
                    <line x1="22.75" x2="21.25" y1="6.25" y2="7.75" fill="none" stroke="#4285f4" strokeLinecap="round"
                          strokeLinejoin="round" className="colorStroke29abe2 svgStroke"></line>
                </svg>
                <h1>404 - Seite nicht gefunden</h1>
                <p>Leider konnten wir die gewünschte Seite nicht finden.</p>
                <p>Klicken Sie <a href="/"> hier um zur Startseite zurückzukehren</a>.</p>
            </div>
        </div>
    );
}

export default PageNotFound404;