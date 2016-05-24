'use strict';

describe('Controller Tests', function() {

    describe('Voucher_state Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVoucher_state;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVoucher_state = jasmine.createSpy('MockVoucher_state');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Voucher_state': MockVoucher_state
            };
            createController = function() {
                $injector.get('$controller')("Voucher_stateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:voucher_stateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
