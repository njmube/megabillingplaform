'use strict';

describe('Controller Tests', function() {

    describe('Type_taxpayer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockType_taxpayer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockType_taxpayer = jasmine.createSpy('MockType_taxpayer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Type_taxpayer': MockType_taxpayer
            };
            createController = function() {
                $injector.get('$controller')("Type_taxpayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:type_taxpayerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
