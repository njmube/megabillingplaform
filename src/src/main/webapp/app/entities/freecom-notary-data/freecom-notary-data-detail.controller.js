(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_notary_dataDetailController', Freecom_notary_dataDetailController);

    Freecom_notary_dataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_notary_data', 'Freecom_public_notaries', 'C_pn_federal_entity'];

    function Freecom_notary_dataDetailController($scope, $rootScope, $stateParams, entity, Freecom_notary_data, Freecom_public_notaries, C_pn_federal_entity) {
        var vm = this;
        vm.freecom_notary_data = entity;
        vm.load = function (id) {
            Freecom_notary_data.get({id: id}, function(result) {
                vm.freecom_notary_data = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_notary_dataUpdate', function(event, result) {
            vm.freecom_notary_data = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
