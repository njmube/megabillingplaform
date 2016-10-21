'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_series_folio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_series_folio, MockTaxpayer_account;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_series_folio = jasmine.createSpy('MockTaxpayer_series_folio');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_series_folio': MockTaxpayer_series_folio,
                'Taxpayer_account': MockTaxpayer_account
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_series_folioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_series_folioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
