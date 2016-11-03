'use strict';

describe('Controller Tests', function() {

    describe('Com_beneficiary Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_beneficiary;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_beneficiary = jasmine.createSpy('MockCom_beneficiary');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_beneficiary': MockCom_beneficiary
            };
            createController = function() {
                $injector.get('$controller')("Com_beneficiaryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_beneficiaryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
