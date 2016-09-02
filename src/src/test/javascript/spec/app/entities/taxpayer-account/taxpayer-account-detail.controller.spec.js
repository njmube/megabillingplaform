'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_account Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_account, MockTax_address, MockTaxpayer_certificate, MockType_taxpayer, MockTax_regime, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            MockTax_address = jasmine.createSpy('MockTax_address');
            MockTaxpayer_certificate = jasmine.createSpy('MockTaxpayer_certificate');
            MockType_taxpayer = jasmine.createSpy('MockType_taxpayer');
            MockTax_regime = jasmine.createSpy('MockTax_regime');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_account': MockTaxpayer_account,
                'Tax_address': MockTax_address,
                'Taxpayer_certificate': MockTaxpayer_certificate,
                'Type_taxpayer': MockType_taxpayer,
                'Tax_regime': MockTax_regime,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_accountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_accountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
