(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_desc_stateDetailController', Com_desc_stateDetailController);

    Com_desc_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_desc_state', 'Com_public_notaries', 'Type_state', 'C_colony', 'C_municipality', 'C_state', 'C_country', 'C_zip_code'];

    function Com_desc_stateDetailController($scope, $rootScope, $stateParams, entity, Com_desc_state, Com_public_notaries, Type_state, C_colony, C_municipality, C_state, C_country, C_zip_code) {
        var vm = this;
        vm.com_desc_state = entity;
        vm.load = function (id) {
            Com_desc_state.get({id: id}, function(result) {
                vm.com_desc_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_desc_stateUpdate', function(event, result) {
            vm.com_desc_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
