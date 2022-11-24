/**
 * 회사 url path이다.
 *
 * @type {string}
 */
const PATH_COR = "/corporation";

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
    const list = url.split('/');
    const path_index = list.indexOf(path.split('/')[-1]);
    let next_path;

    if(path_index < list.length){
        next_path = list[path_index + 1];
    }

    return "/" + next_path;
}