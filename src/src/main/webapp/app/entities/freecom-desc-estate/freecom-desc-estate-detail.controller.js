(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_desc_estateDetailController', Freecom_desc_estateDetailController);

    Freecom_desc_estateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_desc_estate', 'Freecom_public_notaries', 'C_colony', 'C_municipality', 'C_state', 'C_country', 'C_zip_code', 'C_type_estate'];

    function Freecom_desc_estateDetailController($scope, $rootScope, $stateParams, entity, Freecom_desc_estate, Freecom_public_notaries, C_colony, C_municipality, C_state, C_country, C_zip_code, C_type_estate) {
        var vm = this;
        vm.freecom_desc_estate = entity;
        vm.load = function (id) {
            Freecom_desc_estate.get({id: id}, function(result) {
                vm.freecom_desc_estate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_desc_estateUpdate', function(event, result) {
            vm.freecom_desc_estate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
