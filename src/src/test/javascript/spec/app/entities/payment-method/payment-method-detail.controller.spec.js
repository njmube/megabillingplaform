'use strict';

describe('Controller Tests', function() {

    describe('Payment_method Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPayment_method;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPayment_method = jasmine.createSpy('MockPayment_method');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Payment_method': MockPayment_method
            };
            createController = function() {
                $injector.get('$controller')("Payment_methodDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:payment_methodUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
