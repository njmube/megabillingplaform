'use strict';

describe('Controller Tests', function() {

    describe('Com_custom_unit Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_custom_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_custom_unit = jasmine.createSpy('MockCom_custom_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_custom_unit': MockCom_custom_unit
            };
            createController = function() {
                $injector.get('$controller')("Com_custom_unitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_custom_unitUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
