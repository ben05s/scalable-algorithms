import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { McTask } from '../modules/mc/models/McTask';
import { McTaskSettings } from '../modules/mc/models/McTaskSettings';

@Injectable()
export abstract class AbstractService {

    constructor(protected _http: HttpClient) {}

    protected abstract getEndpoint(): string;

    protected buildUrl(uri: string): string {
        return `${this.getEndpoint()}${uri}`;
    }

    getData<T>(uri: string): Observable<T> {
        return this._http.get<T>(this.buildUrl(uri));
    }

    callUrl(uri: string) {
        this._http.get(this.buildUrl(uri));
    }

    post<T>(uri: string, body: any): Observable<T> {
        return this._http.post<T>(this.buildUrl(uri), body);
    }

    get<T>(uri: string): Observable<T> {
        return this._http.get<T>(this.buildUrl(uri));
    }

    postData<T>(uri: string, data: any): Observable<T> {
        return this._http.post<T>(this.buildUrl(uri), data);
    }
}
