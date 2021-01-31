module.exports = {
  siteName: 'Yasen',
  siteDescription: 'Yet Another Selenium Enhancer',
  siteUrl: 'http://yasen.dev/',
  plugins: [
    {
      use: 'gridsome-plugin-typescript'
    },
    {
      use: '@gridsome/source-filesystem',
      options: {
        typeName: 'TextPage',
        baseDir: './content/pages',
        path: '*.md'
      }
    },
    {
      use: '@gridsome/source-filesystem',
      options: {
        typeName: 'TextSubPage',
        baseDir: './content/pages',
        path: '*/**/*.md'
      }
    }
  ],
  templates: {
    TextPage: '/:path',
    TextSubPage: '/:fileInfo__directory/:fileInfo__name'
  }
}
