'use strict';

describe('Controller Tests', function() {

    describe('Com_data_operation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_data_operation, MockCom_public_notaries;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_data_operation = jasmine.createSpy('MockCom_data_operation');
            MockCom_public_notaries = jasmine.createSpy('MockCom_public_notaries');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_data_operation': MockCom_data_operation,
                'Com_public_notaries': MockCom_public_notaries
            };
            createController = function() {
                $injector.get('$controller')("Com_data_operationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_data_operationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
