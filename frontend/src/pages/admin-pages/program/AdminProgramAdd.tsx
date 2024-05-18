import ProgramService from "../../../services/ProgramService.ts";
import React, {useState} from "react";
import {Program} from "../../../types/Program.ts";
import {useNavigate} from "react-router-dom";
import PageHeader from "../../../layouts/PageHeader.tsx";
import ProgramCommonFormFields from "../../../layouts/common-form-fields/ProgramCommonFormFields.tsx";


const programService = new ProgramService();

function AdminProgramAdd() {
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
    const [errorMessage, setErrorMessage] = useState<string>('');
    const navigate = useNavigate();

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        programService.addProgram(program).then(response => {
            console.log(response)
            navigate('/admin/program-list')
            setErrorMessage('')
        }).catch(error => {
            if (error.response) {
                setErrorMessage(error.response.data.message);
            }else {
                setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
            }
        });
    }

    return (
        <main className='container'>
            <PageHeader title="Programm hinzufügen"/>
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

export default AdminProgramAdd;