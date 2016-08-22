(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_addresseeDetailController', Freecom_addresseeDetailController);

    Freecom_addresseeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_addressee', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Freecom_addresseeDetailController($scope, $rootScope, $stateParams, entity, Freecom_addressee, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.freecom_addressee = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_addresseeUpdate', function(event, result) {
            vm.freecom_addressee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
