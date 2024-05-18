import {Program} from "../../types/Program.ts";

function ProgramCommonFormFields({program, setProgram}: Readonly<{ program: Program, setProgram: (program: Program) => void }>) {
    return (
        <div className="row g-3">

            <div className="col-sm-6">
                <label htmlFor="name" className="form-label">Programmname</label>
                <input type="text" className="form-control" id="name"
                       placeholder="Schreiben Sie den Kategorienamen" value={program.name}
                       onChange={(e) => setProgram({...program, name: e.target.value})}/>
            </div>

            <div className="col-sm-6">
                <label htmlFor="description" className="form-label">Beschreibung</label>
                <input type="text" className="form-control" id="description"
                       placeholder="Schreiben Sie die Beschreibung" value={program.description}
                       onChange={(e) => setProgram({...program, description: e.target.value})}/>
            </div>

            <div className="col-sm-12">
                <label htmlFor="imageUrl" className="form-label">Bild URL</label>
                <input type="text" className="form-control" id="imageUrl"
                       placeholder="Schreiben Sie die Beschreibung" value={program.imageUrl}
                       onChange={(e) => setProgram({...program, imageUrl: e.target.value})}/>
            </div>

            <div className="col-sm-12">
                <label htmlFor="marketingImageUrl" className="form-label">Marketing-Bild-URL</label>
                <input type="text" className="form-control" id="marketingImageUrl"
                       placeholder="Schreiben Sie die Beschreibung" value={program.imageUrl}
                       onChange={(e) => setProgram({...program, imageUrl: e.target.value})}/>
            </div>
        </div>
    );
}

export default ProgramCommonFormFields;