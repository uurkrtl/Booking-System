import {Program} from "./Program.ts";
import {Location} from "./Location.ts";

export type Course = {
    id: string,
    programId: string,
    programName: string,
    locationId: string,
    locationName: string,
    programDescription: string,
    programImageUrl: string,
    quota: number,
    trainer: string,
    startDate: Date,
    endDate: Date,
    price: number,
    duration: string,
    timePlan: string,
    timePlanExcepted: string,
    status: string,
    createdAt: Date,
    updatedAt: Date
    program: Program,
    location: Location
}