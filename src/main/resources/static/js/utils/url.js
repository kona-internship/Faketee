/**
 * 회사 url path이다.
 *
 * @type {string}
 */
const PATH_COR = "/corporation";

/**
 * 조직 url path이다.
 *
 * @type {string}
 */
const PATH_DEP = "/dep";
/**
 * 근무일정 url path이다.
 * @type {string}
 */
const PATH_SCH = "/sch";

/**
 * 근무일정 템플릿 url path
 */
const PATH_TMP = "/template";

/**
 * 직원 url path이다.
 *
 * @type {string}
 */
const PATH_EMP = "/emp";

/**
 * 근태 url path이다.
 * @type {string}
 */
const PATH_ATD = "/atd";

/**
 * 출퇴근기록 요청 url path이다.
 * @type {string}
 */
const PATH_ATD_REQ = "/atd/req";

/**
 * 회사 선택 후 url에 들어가는 prefix이다.
 *
 * @type {string}
 */
const URL_COR_PREFIX = PATH_COR;

/**
 * 회사 선택후 Api 통신시 url에 들어가는 prefix이다.
 *
 * @type {string}
 */
const URL_API_COR_PREFIX = "/api" + URL_COR_PREFIX;


/**
 * url의 다음 path를 리턴한다.
 * 전체 url에서 파라미터로 받은 path의 다음 path를 리턴해준다.
 * '/'을 구분자로 다음 path를 가져온다.
 *
 * @param url 전체 url
 * @param path 가져오고자 하는 path의 전 path
 * @returns {*}
 */
function getNextPath(url, path){

    const pathList = path.split('/');
    const urlList = url.split('/');
    const path_index = urlList.indexOf(pathList[pathList.length-1]);
    let next_path;

    if(path_index < urlList.length){
        next_path = urlList[path_index + 1];
    }

    return "/" + next_path;
}