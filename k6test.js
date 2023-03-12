import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    vus: 1000,
    iterations: 50000,
};

export default function () {
    http.get('http://localhost:8080/answer?value=123');
}