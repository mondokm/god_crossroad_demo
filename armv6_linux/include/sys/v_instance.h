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


#ifndef V_INSTANCE_H
#define V_INSTANCE_H

#include "v_kernel.h"

#define v_instance(o) (C_CAST(o,v_instance))

#define v_instanceState(_this) (v_instance(_this)->state)
#define v_instanceSetState(_this, _state) (v_instance(_this)->state = _state)
#define v_instanceEntity(_this) (v_instance(_this)->entity)
#define v_instanceSetEntity(_this, _entity) (v_instance(_this)->entity = _entity)

void
v_instanceInit (
    v_instance _this,
    v_entity entity);

void
v_instanceDeinit (
    v_instance _this);

#endif
