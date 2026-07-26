import http from 'k6/http';
import { check } from 'k6';

// 프로젝트 모집글 상세 조회 부하 테스트
// 사용법: k6 run -e TOKEN=<JWT> -e BASE_URL=http://localhost:8090 k6/project-detail.js
export const options = {
    vus: 50,          // 동시 사용자 50명
    duration: '60s',  // 60초 동안 반복 조회
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8090';
const TOKEN = __ENV.TOKEN;

export default function () {
    const res = http.get(`${BASE_URL}/api/project-recruitments/1`, {
        headers: { Authorization: `Bearer ${TOKEN}` },
    });

    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}
