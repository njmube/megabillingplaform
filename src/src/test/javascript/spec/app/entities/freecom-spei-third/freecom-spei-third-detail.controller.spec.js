'use strict';

describe('Controller Tests', function() {

    describe('Freecom_spei_third Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_spei_third, MockFreecom_spei, MockFreecom_payer, MockFreecom_beneficiary;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_spei_third = jasmine.createSpy('MockFreecom_spei_third');
            MockFreecom_spei = jasmine.createSpy('MockFreecom_spei');
            MockFreecom_payer = jasmine.createSpy('MockFreecom_payer');
            MockFreecom_beneficiary = jasmine.createSpy('MockFreecom_beneficiary');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_spei_third': MockFreecom_spei_third,
                'Freecom_spei': MockFreecom_spei,
                'Freecom_payer': MockFreecom_payer,
                'Freecom_beneficiary': MockFreecom_beneficiary
            };
            createController = function() {
                $injector.get('$controller')("Freecom_spei_thirdDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_spei_thirdUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
