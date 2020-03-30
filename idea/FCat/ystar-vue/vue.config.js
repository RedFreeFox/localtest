module.exports = {
  // devServer: {
  //   disableHostCheck: true
  // },
  // 配置 webpack-dev-server 行为。
	devServer: {
    disableHostCheck: true,
		open: process.platform === 'darwin',
		host: '0.0.0.0',
		port: 8085,
		https: false,
		hotOnly: false,
		proxy: {
			'/api': { // search为转发路径
				target: 'http://coin.xfdmao.com', // 目标地址
				changeOrigin: true, // 设置同源  默认false，是否需要改变原始主机头为目标URL,    
				pathRewrite: {
					'^/api': '/api' //通过pathRewrite重写地址，将前缀/api转为/
				}
			}
		},
		before: app => {},
	}
}
