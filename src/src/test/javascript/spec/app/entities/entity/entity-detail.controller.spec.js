'use strict';

describe('Controller Tests', function() {

    describe('Entity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEntity, MockAccounting, MockKey_entity, MockScope, MockFreecom_ine;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEntity = jasmine.createSpy('MockEntity');
            MockAccounting = jasmine.createSpy('MockAccounting');
            MockKey_entity = jasmine.createSpy('MockKey_entity');
            MockScope = jasmine.createSpy('MockScope');
            MockFreecom_ine = jasmine.createSpy('MockFreecom_ine');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Entity': MockEntity,
                'Accounting': MockAccounting,
                'Key_entity': MockKey_entity,
                'Scope': MockScope,
                'Freecom_ine': MockFreecom_ine
            };
            createController = function() {
                $injector.get('$controller')("EntityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:entityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
