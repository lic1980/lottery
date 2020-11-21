import Login from '@/components/Login'
import Lottery from '@/components/Lottery'
import Bid from '@/components/Bid'
import Register from '@/components/Register'

const routers = [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/lottery',
      name: 'Lottery',
      component: Lottery
    },
    {
      path: '/bid',
      name: 'Bid',
      component: Bid
    }
]
export default routers