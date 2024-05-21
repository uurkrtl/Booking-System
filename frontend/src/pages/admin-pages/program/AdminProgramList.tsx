import ProgramService from "../../../services/ProgramService.ts";
import {useEffect, useState} from "react";
import {Program} from "../../../types/Program.ts";
import {Link} from "react-router-dom";
import PageHeader from "../../../layouts/PageHeader.tsx";

const programService = new ProgramService();

function AdminProgramList() {
    const [programs, setPrograms] = useState<Program[]>([]);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [filterByName, setFilterByName] = useState("");

    const filteredPrograms = programs.filter(
        (program) =>
            program.name.toLowerCase().includes(filterByName.toLowerCase())
    );

    useEffect(() => {
        programService.getAllPrograms().then((response) => {
            setPrograms(response.data)
            setErrorMessage('')
            setLoading(false)
        }).catch(error => {
            setPrograms([])
            setLoading(false)
            setErrorMessage(error.message)
        })
    }, []);

    const truncateText = (text: string, maxLength: number) => {
        if (text.length > maxLength) {
            return text.substring(0, maxLength - 3) + '...';
        }
        return text;
    };

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
            <PageHeader title={'Programmliste'}/>

            <div className="d-flex justify-content-end">
                <Link to={"/admin/program/add"} className="btn btn-outline-secondary mb-3">Programm erstellen</Link>
            </div>

            <div className="input-group">
                <span className="input-group-text" id="basic-addon3">Schreiben einen Namensfilter</span>
                <input
                    type="text"
                    className="form-control"
                    id="basic-url"
                    aria-describedby="basic-addon3 basic-addon4"
                    onChange={(e) => setFilterByName(e.target.value)}
                />
            </div>

            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Status</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {filteredPrograms.map((program) => {
                    return (
                        <tr key={program.id}>
                            <td>{truncateText(program.name, 40)}</td>
                            <td>
                                {program.status ?
                                    <span className="badge text-bg-success rounded-pill">Aktiv</span>
                                    : <span className="badge text-bg-danger rounded-pill">Passiv</span>}
                            </td>
                            <td><Link to={`/admin/program/detail/${program.id}`}
                                      className="btn btn-outline-info">Detail</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
            {errorMessage && (
                <div className="alert alert-danger" role="alert">
                    {errorMessage}
                </div>
            )}
        </div>
    );
}

export default AdminProgramList;