/**
 * Define all of your application routes here
 * for more information on routes, see the
 * official documentation https://router.vuejs.org/en/
 */
export default [
	{
    path: '/twoMaStrategyHistory',
    name: '收益及交易历史',
    view: 'twoMaStrategyHistory'
  },
  {
    path: '/twoMaStrategyBest',
    name: '最佳策略',
    view: 'twoMaStrategyBest'
  },
  {
    path: '/KLine',
    name: 'K线图',
    view: 'KLine'
  },
  {
    path: '/dashboard',
    // Relative to /src/views
    view: 'Dashboard'
  },
  {
    path: '/user-profile',
    name: 'User Profile',
    view: 'UserProfile'
  },
  {
    path: '/table-list',
    name: 'Table List',
    view: 'TableList'
  },
  {
    path: '/typography',
    view: 'Typography'
  },
  {
    path: '/icons',
    view: 'Icons'
  },
  {
    path: '/maps',
    view: 'Maps'
  },
  {
    path: '/notifications',
    view: 'Notifications'
  },
  {
    path: '/upgrade',
    name: 'Upgrade to PRO',
    view: 'Upgrade'
  }
]
