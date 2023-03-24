import http from 'k6/http';

export const options = {
    vus: 100,
    iterations: 1000,
};

export default function () {
    http.get('http://localhost:8080/answer?value=123');
}