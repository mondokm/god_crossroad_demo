/*
 *                         OpenSplice DDS
 *
 *   This software and documentation are Copyright 2006 to  PrismTech
 *   Limited, its affiliated companies and licensors. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
#ifndef SACPP_DEFAULTVALUEREFCOUNTBASE_H
#define SACPP_DEFAULTVALUEREFCOUNTBASE_H

#include "sacpp_ValueBase.h"
#include "sacpp_Counter.h"
#include "cpp_dcps_if.h"

namespace DDS
{
   class OS_API DefaultValueRefCountBase: public virtual ValueBase
   {
      public:
         virtual ValueBase* _add_ref ();

         virtual void _remove_ref ();

         virtual ULong _refcount_value ();

      protected:
         DefaultValueRefCountBase ();

         virtual ~DefaultValueRefCountBase () {}

      private:
         DDS_DCPS_Counter m_count;
   };
}
#undef OS_API

#endif /* SACPP_DEFAULTVALUEREFCOUNTBASE_H */
