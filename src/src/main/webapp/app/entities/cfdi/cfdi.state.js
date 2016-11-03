(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfdi', {
            parent: 'entity',
            url: '/cfdi?page&sort&search',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.cfdi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi/cfdis.html',
                    controller: 'CfdiController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-new', {
            parent: 'entity',
            url: '/cfdi-new/{id}',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.cfdi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi/cfdi-new.html',
                    controller: 'CfdiNewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: [function () {
                    return {
                        version: null,
                        serial: null,
                        folio: null,
                        date_expedition: null,
                        payment_conditions: null,
                        change_type: (1).toFixed(2),
                        place_expedition: null,
                        account_number: null,
                        folio_fiscal_orig: null,
                        serial_folio_fiscal_orig: null,
                        date_folio_fiscal_orig: null,
                        mont_folio_fiscal_orig: null,
                        total_tax_retention: null,
                        total_tax_transfered: null,
                        discount: (0).toFixed(2),
                        discount_reason: null,
                        subtotal: (0).toFixed(2),
                        total: (0).toFixed(2),
                        addenda: null,
                        number_certificate: null,
                        certificate: null,
                        id: null
                    };
                }],
                taxpayer_account_entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                    return Taxpayer_account.get({id : $stateParams.id}).$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi');
                    $translatePartialLoader.addPart('taxpayer_account');
                    $translatePartialLoader.addPart('taxpayer_client');
                    $translatePartialLoader.addPart('client_address');
                    $translatePartialLoader.addPart('customs_info');
                    $translatePartialLoader.addPart('part_concept');
                    $translatePartialLoader.addPart('customs_info_part');
                    $translatePartialLoader.addPart('com_taxregistration');
                    $translatePartialLoader.addPart('com_pfic');
                    $translatePartialLoader.addPart('com_accreditation_ieps');
                    $translatePartialLoader.addPart('com_taxlegends');
                    $translatePartialLoader.addPart('com_legends');
                    $translatePartialLoader.addPart('com_airline');
                    $translatePartialLoader.addPart('com_charge');
                    $translatePartialLoader.addPart('com_apaw');
                    $translatePartialLoader.addPart('com_donees');
                    $translatePartialLoader.addPart('com_educational_institutions');
                    $translatePartialLoader.addPart('com_ine');
                    $translatePartialLoader.addPart('com_ine_entity');
                    $translatePartialLoader.addPart('com_accounting');
                    $translatePartialLoader.addPart('com_kind_payment');
                    $translatePartialLoader.addPart('com_foreign_tourist_passenger');
                    $translatePartialLoader.addPart('com_partial_construction_services');
                    $translatePartialLoader.addPart('com_foreign_exchange');
                    $translatePartialLoader.addPart('com_local_taxes');
                    $translatePartialLoader.addPart('com_local_retentions');
                    $translatePartialLoader.addPart('com_local_transfered');
                    $translatePartialLoader.addPart('com_used_vehicle');
                    $translatePartialLoader.addPart('com_vehicle_customs_information');
                    $translatePartialLoader.addPart('com_destruction_certificate');
                    $translatePartialLoader.addPart('com_info_customs_destruction');
                    $translatePartialLoader.addPart('com_fuel_consumption');
                    $translatePartialLoader.addPart('com_concept_fuel');
                    $translatePartialLoader.addPart('com_determined');
                    $translatePartialLoader.addPart('com_storeroom_paybill');
                    $translatePartialLoader.addPart('com_paybill_concept');
                    $translatePartialLoader.addPart('com_ecc_11');
                    $translatePartialLoader.addPart('com_ecc_11_concept');
                    $translatePartialLoader.addPart('com_ecc_11_transfer');
                    $translatePartialLoader.addPart('com_spei');
                    $translatePartialLoader.addPart('com_spei_third');
                    $translatePartialLoader.addPart('com_payer');
                    $translatePartialLoader.addPart('com_beneficiary');
                    $translatePartialLoader.addPart('com_foreign_trade');
                    $translatePartialLoader.addPart('com_addressee');
                    $translatePartialLoader.addPart('com_commodity');
                    $translatePartialLoader.addPart('com_specific_descriptions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-detail', {
            parent: 'entity',
            url: '/cfdi/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi/cfdi-detail.html',
                    controller: 'CfdiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfdi', function($stateParams, Cfdi) {
                    return Cfdi.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('cfdi.new', {
            parent: 'cfdi',
            url: '/new',
            data: {
                authorities: ['ROLE_AFILITATED']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi/cfdi-dialog.html',
                    controller: 'CfdiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                serial: null,
                                folio: null,
                                date_expedition: null,
                                payment_conditions: null,
                                change_type: null,
                                place_expedition: null,
                                account_number: null,
                                folio_fiscal_orig: null,
                                serial_folio_fiscal_orig: null,
                                date_folio_fiscal_orig: null,
                                mont_folio_fiscal_orig: null,
                                total_tax_retention: null,
                                total_tax_transfered: null,
                                discount: null,
                                discount_reason: null,
                                subtotal: null,
                                total: null,
                                addenda: null,
                                number_certificate: null,
                                certificate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cfdi', null, { reload: true });
                }, function() {
                    $state.go('cfdi');
                });
            }]
        })
        .state('cfdi.edit', {
            parent: 'cfdi',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi/cfdi-dialog.html',
                    controller: 'CfdiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfdi', function(Cfdi) {
                            return Cfdi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfdi.delete', {
            parent: 'cfdi',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi/cfdi-delete-dialog.html',
                    controller: 'CfdiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfdi', function(Cfdi) {
                            return Cfdi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
