(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_desc_estateDetailController', Com_desc_estateDetailController);

    Com_desc_estateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_desc_estate', 'Com_public_notaries', 'C_colony', 'C_municipality', 'C_state', 'C_country', 'C_zip_code', 'C_type_estate'];

    function Com_desc_estateDetailController($scope, $rootScope, $stateParams, entity, Com_desc_estate, Com_public_notaries, C_colony, C_municipality, C_state, C_country, C_zip_code, C_type_estate) {
        var vm = this;
        vm.com_desc_estate = entity;
        vm.load = function (id) {
            Com_desc_estate.get({id: id}, function(result) {
                vm.com_desc_estate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_desc_estateUpdate', function(event, result) {
            vm.com_desc_estate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
