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
#ifndef C_FILTER_H
#define C_FILTER_H

#include "c_base.h"
#include "os_if.h"
#include "c_querybase.h"

#ifdef OSPL_BUILD_CORE
#define OS_API OS_API_EXPORT
#else
#define OS_API OS_API_IMPORT
#endif

#if defined (__cplusplus)
extern "C" {
#endif
/* !!!!!!!!NOTE From here no more includes are allowed!!!!!!! */

C_CLASS(c_filter);

OS_API c_filter
c_filterNew(
    c_type type,
    q_expr predicate,
    const c_value params[]);

OS_API c_bool
c_filterEval(
    c_filter f,
    c_object o);

OS_API c_bool
c_qPredEval(
    c_qPred q,
    c_object o);

#undef OS_API

#if defined (__cplusplus)
}
#endif

#endif /* C_FILTER_H */
