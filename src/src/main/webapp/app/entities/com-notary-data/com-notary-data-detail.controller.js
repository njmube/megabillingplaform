(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_notary_dataDetailController', Com_notary_dataDetailController);

    Com_notary_dataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_notary_data', 'Com_public_notaries', 'Public_notaries_federal_entity'];

    function Com_notary_dataDetailController($scope, $rootScope, $stateParams, entity, Com_notary_data, Com_public_notaries, Public_notaries_federal_entity) {
        var vm = this;
        vm.com_notary_data = entity;
        vm.load = function (id) {
            Com_notary_data.get({id: id}, function(result) {
                vm.com_notary_data = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_notary_dataUpdate', function(event, result) {
            vm.com_notary_data = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
