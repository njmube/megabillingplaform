'use strict';

describe('Controller Tests', function() {

    describe('C_type_operation_ce Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_type_operation_ce;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_type_operation_ce = jasmine.createSpy('MockC_type_operation_ce');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_type_operation_ce': MockC_type_operation_ce
            };
            createController = function() {
                $injector.get('$controller')("C_type_operation_ceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_type_operation_ceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
