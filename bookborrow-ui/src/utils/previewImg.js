/**
 * 拼接完整图片地址
 * @param {string} path 相对路径，如 /profile/upload/xxx.jpg
 * @returns {string} 完整 URL
 */
export function resolveImageUrl(path) {
  if (!path) return '';
  if (path.startsWith('http')) return path;

  const base = window.location.origin;
  const apiPrefix = process.env.VUE_APP_BASE_API || '';

  // 注意去除双斜杠
  return `${base}${apiPrefix}${path}`.replace(/([^:]\/)\/+/g, '$1');
}

export function imageUrlFilter(path) {
  if (!path) return '';
  if (path.startsWith('http')) return path;

  const base = window.location.origin;
  const prefix = process.env.VUE_APP_BASE_API || '';
  return `${base}${prefix}${path}`.replace(/([^:]\/)\/+/g, '$1');
}
