import { HttpHeaders } from '@angular/common/http';

// baseURL is empty because everything is on the same baseURL as the UI
export const baseURL = '';
export const compileBaseURL = '';

export const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };
