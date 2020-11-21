import Login from '@/components/Login'
import Register from '@/components/Register'
import AgentAdmin from '@/components/AgentAdmin'
import BidsAdmin from '@/components/BidsAdmin'
import CustomersAdmin from '@/components/CustomersAdmin'

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
      path: '/bid',
      name: 'Bid',
      component: BidsAdmin
    }
    ,
    {
      path: '/customer',
      name: 'Customer',
      component: CustomersAdmin
    },
    {
      path: '/agent',
      name: 'Agent',
      component: AgentAdmin
    }
]
export default routers