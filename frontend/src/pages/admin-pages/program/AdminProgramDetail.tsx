import ProgramService from "../../../services/ProgramService.ts";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Program} from "../../../types/Program.ts";


const programService = new ProgramService();

function AdminProgramDetail() {
    const { programId = '' } = useParams<string>();
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
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const handleStatusChange = (status: boolean) => {
        programService.changeProgramStatus(programId, status).then(() => {
            setProgram({
                ...program,
                status: status
            })
        }).catch((error) => {
            if (error.response) {
                if (error.response.status === 403) {
                    setErrorMessage('Sie haben keine Berechtigung, den Status zu ändern.');
                }else {
                    setErrorMessage(error.response.data.message)
                }
            } else {
                setErrorMessage('Fehler beim Ändern des Status: ' + error.message);
            }
        })
    }

    useEffect(() => {
        programService.getProgramById(programId).then((response) => {
            setProgram(response.data)
            setLoading(false)
            setErrorMessage('')
        }).catch(error => {
            setProgram({} as Program)
            setLoading(false)
            setErrorMessage(error.message)
            navigate('*')
        })
    }, [programId, navigate]);

    if (loading) {
        return <div className={'container mt-1'}>
            <div className={'spinner-border text-primary mt-3'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <div className={'container'}>
            <div className="row flex-lg-row-reverse align-items-center g-5 py-2">
                <div className="col-10 col-sm-8 col-lg-6">
                    <img src={program?.imageUrl} className="d-block mx-lg-auto img-fluid" alt="Product"
                         width="400" height="300" loading="lazy"/>
                </div>
                <div className="col-lg-6">
                    <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">{program?.name}</h1>
                    <p className="lead">{program?.description}</p>

                    <table className="table table-striped-columns">
                        <tbody>
                        <tr>
                            <th scope="row">Erstellung</th>
                            <td>{program?.createdAt ? new Date(program?.createdAt).toLocaleString('de-DE') : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Letzte Aktualisierung</th>
                            <td>{program?.updatedAt ? new Date(program?.updatedAt).toLocaleString('de-DE') : "-"}</td>
                        </tr>
                        <tr>
                            <th scope="row">Status</th>
                            <td>{program?.status ?
                                <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                : <span className="badge text-bg-danger rounded-pill">Passiv</span>}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div className="d-grid gap-2 d-md-flex justify-content-md-start">
                        <Link to={`/admin/program/update/${programId}`} type="button"
                              className="btn btn-primary btn-lg px-4 me-md-2">Aktualisieren</Link>
                        <button type="button"
                                className={program?.status ? 'btn btn-danger px-4 me-md-2' : 'btn btn-success px-4 me-md-2'}
                                onClick={() => handleStatusChange(!program?.status)}>
                            {program?.status ? 'Deaktivieren' : 'Aktivieren'}</button>
                        <Link to={`/admin/program-list`} type="button"
                              className="btn btn-outline-secondary btn-lg px-4">Programmliste</Link>
                    </div>
                    {errorMessage && (
                        <div className="alert alert-danger mt-3" role="alert">
                            {errorMessage}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default AdminProgramDetail;