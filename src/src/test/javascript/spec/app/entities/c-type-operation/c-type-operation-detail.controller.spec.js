'use strict';

describe('Controller Tests', function() {

    describe('C_type_operation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_type_operation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_type_operation = jasmine.createSpy('MockC_type_operation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_type_operation': MockC_type_operation
            };
            createController = function() {
                $injector.get('$controller')("C_type_operationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_type_operationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
