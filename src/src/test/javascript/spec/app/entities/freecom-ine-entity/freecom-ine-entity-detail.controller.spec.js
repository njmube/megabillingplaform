'use strict';

describe('Controller Tests', function() {

    describe('Freecom_ine_entity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_ine_entity, MockKey_entity, MockFreecom_ine, MockC_scope_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_ine_entity = jasmine.createSpy('MockFreecom_ine_entity');
            MockKey_entity = jasmine.createSpy('MockKey_entity');
            MockFreecom_ine = jasmine.createSpy('MockFreecom_ine');
            MockC_scope_type = jasmine.createSpy('MockC_scope_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_ine_entity': MockFreecom_ine_entity,
                'Key_entity': MockKey_entity,
                'Freecom_ine': MockFreecom_ine,
                'C_scope_type': MockC_scope_type
            };
            createController = function() {
                $injector.get('$controller')("Freecom_ine_entityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_ine_entityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
