'use strict';

describe('Controller Tests', function() {

    describe('Ring_pack Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRing_pack;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRing_pack = jasmine.createSpy('MockRing_pack');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Ring_pack': MockRing_pack
            };
            createController = function() {
                $injector.get('$controller')("Ring_packDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:ring_packUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
