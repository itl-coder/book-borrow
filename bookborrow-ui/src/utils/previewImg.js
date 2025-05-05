/**
 * 全局过滤器
 * @param path
 * @returns {*|string}
 */
export function imageUrlFilter(path) {
  if (!path) return '';
  if (path.startsWith('http')) return path;

  const base = window.location.origin;
  const prefix = process.env.VUE_APP_BASE_API || '';
  return `${base}${prefix}${path}`.replace(/([^:]\/)\/+/g, '$1');
}

/**
 * 默认封面图的处理
 */
export function bookCoverUrl(path) {
  // 从环境变量中获取默认封面路径
  const defaultCover = process.env.DEFAULT_BOOK_COVER || '/profile/upload/2025/05/05/default_book_cover_20250505115723A001.png';

  // 处理默认封面，如果路径为空或为假值，使用默认封面
  if (!path || path.trim() === '') {
    console.log("into ...")
    // 如果默认封面路径存在且有效，使用默认封面
    if (defaultCover) {
      // 使用 imageUrlFilter 处理默认封面路径
      const finalDefaultCover = imageUrlFilter(defaultCover);
      return finalDefaultCover || '';  // 如果返回空，使用空字符串
    }
    return '';  // 如果没有默认封面路径，返回空字符串
  }

  // 如果路径已经是 http 开头，则直接返回
  if (path.startsWith('http')) return path;

  // 拼接路径
  const base = window.location.origin;
  const prefix = process.env.VUE_APP_BASE_API || '';
  return `${base}${prefix}${path}`.replace(/([^:]\/)\/+/g, '$1');
}
