interface InfoResponse {
    name: string;
    version: string;
    commitHash: string;
}

interface InfoApi {
	getInfo(): Promise<InfoResponse>;
}