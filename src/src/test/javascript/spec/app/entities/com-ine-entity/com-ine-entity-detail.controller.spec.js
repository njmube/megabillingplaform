'use strict';

describe('Controller Tests', function() {

    describe('Com_ine_entity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_ine_entity, MockKey_entity, MockCom_ine, MockC_scope_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_ine_entity = jasmine.createSpy('MockCom_ine_entity');
            MockKey_entity = jasmine.createSpy('MockKey_entity');
            MockCom_ine = jasmine.createSpy('MockCom_ine');
            MockC_scope_type = jasmine.createSpy('MockC_scope_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_ine_entity': MockCom_ine_entity,
                'Key_entity': MockKey_entity,
                'Com_ine': MockCom_ine,
                'C_scope_type': MockC_scope_type
            };
            createController = function() {
                $injector.get('$controller')("Com_ine_entityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_ine_entityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
