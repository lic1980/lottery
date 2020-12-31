
import AgentsAdmin from '@/components/AgentsAdmin'
import LotteriesAdmin from '@/components/LotteriesAdmin'

const routers = [
    {
      path: '/agents',
      name: 'Agents',
      component: AgentsAdmin
    },
    {
      path: '/lotteries',
      name: 'Lotteries',
      component: LotteriesAdmin
    }
]
export default routers