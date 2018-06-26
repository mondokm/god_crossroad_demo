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
#ifndef CPP_DDS_OPENSPLICE_LISTENERDISPATCHER_H
#define CPP_DDS_OPENSPLICE_LISTENERDISPATCHER_H

#include "cmn_listenerDispatcher.h"

#include "ccpp.h"
#include "cpp_dcps_if.h"

namespace DDS {

    namespace OpenSplice {

        class OS_API ListenerDispatcher {
        public:
            static os_schedClass
            scheduling_class (
                const DDS::SchedulingQosPolicy &scheduling);

            static os_int32
            scheduling_priority (
                const DDS::SchedulingQosPolicy &scheduling);

            static void
            event_handler (
                v_listenerEvent event,
                c_voidp argument);
        };

}; /* namespace OpenSplice */
}; /* namespace DDS */

#undef OS_API
#endif /* CPP_DDS_OPENSPLICE_LISTENERDISPATCHER_H */