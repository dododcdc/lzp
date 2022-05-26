'use strict'

const path = require('path');

function resolve(dir) {
    return path.join(__dirname, dir);
}
module.exports = {
    productionSourceMap: false,
    lintOnSave: true,
    chainWebpack: (config) => {
        config.resolve.alias
            .set('@', resolve('./src'))
            .set('@assets', resolve('./src/assets'))
    },
    publicPath:'/lzp/'
};
