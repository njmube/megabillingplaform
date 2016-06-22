'use strict';

describe('Controller Tests', function() {

    describe('Way_payment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockWay_payment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockWay_payment = jasmine.createSpy('MockWay_payment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Way_payment': MockWay_payment
            };
            createController = function() {
                $injector.get('$controller')("Way_paymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:way_paymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
