import ProgramService from "../../../services/ProgramService.ts";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {Program} from "../../../types/Program.ts";
import PageHeader from "../../../layouts/PageHeader.tsx";
import ProgramCommonFormFields from "../../../layouts/common-form-fields/ProgramCommonFormFields.tsx";

const programService = new ProgramService();

function AdminProgramUpdate() {
    const [program, setProgram] = useState<Program>({
        id: '',
        name: '',
        description: '',
        imageUrl: '',
        marketingImageUrl: '',
        createdAt: new Date(),
        updatedAt: new Date(),
        status: true
    });
    const navigate = useNavigate();
    const {programId = ''} = useParams();
    const [errorMessage, setErrorMessage] = useState<string>('');

    useEffect(() => {
        if (programId) {
            programService.getProgramById(programId).then((response) => {
                setProgram(response.data)
                setErrorMessage('')
            }).catch(error => {
                setProgram({} as Program)
                setErrorMessage('Fehler beim Abrufen der Kategorie: ' + error);
                navigate('*');
            })
        }
    }, [programId, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        programService.updateProgram(programId, program).then(response => {
            console.log(response)
            navigate(`/`)
        }).catch(error => {
            if (error.response) {
                console.log(error.response.data);
                setErrorMessage(error.response.data.message);
            } else {
                console.log('Etwas ist schief gelaufen:', error.message);
                setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
            }
        })
    }

    return (
        <main className='container'>
            <PageHeader title={'Programmaktualisierung'}/>
            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <ProgramCommonFormFields program={program} setProgram={setProgram}/>
                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Speichern</button>
                    </form>
                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}
                </div>
            </div>
        </main>
    );
}

export default AdminProgramUpdate;