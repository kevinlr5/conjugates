export class HttpError extends Error {
    public status: number;
    public statusText: string;
    public result: any;
    constructor(status: number, statusText: string, result: any) {
        super(statusText);
        Object.setPrototypeOf(this, HttpError.prototype);
        this.status = status;
        this.statusText = statusText;
        this.result = result;
    }
}

function getResult(res: Response): Promise<any> {
    const contentType = res.headers.get('Content-Type');
    if (res.status === 204) {
        return Promise.resolve();
    } else if (contentType && contentType.startsWith('application/json')) {
        return res.json();
    } else {
        return res.text();
    }
}

function handleResponse<T>(res: Response): Promise<T> {
    return getResult(res).then(result => {
        if (res.ok) {
            return result;
        } else {
            throw new HttpError(res.status, res.statusText, result);
        }
    });
}

export function post<T>(url: string, data?: {}): Promise<T> {
    return fetch(url, {
        method: 'POST',
        headers: new Headers({
            'content-type': 'application/json',
        }),
        body: JSON.stringify(data),
    }).then(res => handleResponse<T>(res));
}

export function get<T>(url: string): Promise<T> {
    return fetch(url, {
        method: 'GET',
        headers: new Headers({
            'content-type': 'application/json',
        }),
    }).then(res => handleResponse<T>(res));
}
