'use strict';

describe('Controller Tests', function() {

    describe('Voucher_type Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVoucher_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVoucher_type = jasmine.createSpy('MockVoucher_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Voucher_type': MockVoucher_type
            };
            createController = function() {
                $injector.get('$controller')("Voucher_typeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:voucher_typeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
