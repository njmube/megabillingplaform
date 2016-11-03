(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_tourist_passengerDetailController', Com_foreign_tourist_passengerDetailController);

    Com_foreign_tourist_passengerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_foreign_tourist_passenger', 'C_transit_type', 'C_type_road', 'Cfdi'];

    function Com_foreign_tourist_passengerDetailController($scope, $rootScope, $stateParams, entity, Com_foreign_tourist_passenger, C_transit_type, C_type_road, Cfdi) {
        var vm = this;
        vm.com_foreign_tourist_passenger = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_foreign_tourist_passengerUpdate', function(event, result) {
            vm.com_foreign_tourist_passenger = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
